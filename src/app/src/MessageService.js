import { RSocketClient, MAX_STREAM_ID } from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';
import { encodeAndAddWellKnownMetadata } from 'rsocket-core';
import {
  BufferEncoders,
  MESSAGE_RSOCKET_COMPOSITE_METADATA,
  MESSAGE_RSOCKET_ROUTING
} from 'rsocket-core';

export default class MessageService {
  constructor(connectCallback, messageCallback) {
    this._client = new RSocketClient({
      setup: {
        keepAlive: 5000,
        lifetime: 30000,
        dataMimeType: 'text/plain',
        metadataMimeType: MESSAGE_RSOCKET_COMPOSITE_METADATA.string,
      },
      transport: new RSocketWebSocketClient({
        // eslint-disable-next-line no-restricted-globals
        url: `ws://${location.host}/ws`
      }, BufferEncoders),
    });
    this._connectCallback = connectCallback;
    this._messageCallback = messageCallback;
  }

  connect() {
    this._client.connect().then(
      (socket) => {
        this._socket = socket;
        this._connectCallback(null);
      },
      (error) => this._connectCallback(error),
    );
  }

  send(message) {
    const routeMetadata = this.encodeRoute('stringSplit');
    const metadata = encodeAndAddWellKnownMetadata(
      Buffer.alloc(0),
      MESSAGE_RSOCKET_ROUTING,
      routeMetadata
    );
    this._socket.requestStream({
      data: Buffer.from(message, 'utf8'),
      metadata,
    }).subscribe({
      onNext: (payload) => this._messageCallback(null, payload.data.toString('utf8')),
      onError: (error) => this._messageCallback(error),
      onSubscribe: (_subscription) => _subscription.request(MAX_STREAM_ID),
    });
  }

  encodeRoute(route) {
    const length = Buffer.byteLength(route, 'utf8');
    const buffer = Buffer.alloc(1);
    buffer.writeInt8(length);
    return Buffer.concat([buffer, Buffer.from(route, 'utf8')]);
  }
}
