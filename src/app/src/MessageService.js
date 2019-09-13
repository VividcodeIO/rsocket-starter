import {RSocketClient, MAX_STREAM_ID} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';

export default class MessageService {
  constructor(connectCallback, messageCallback) {
    this._client = new RSocketClient({
      setup: {
        keepAlive: 10000,
        dataMimeType: 'text/plain',
        metadataMimeType: 'text/plain',
      },
      transport: new RSocketWebSocketClient({
        // eslint-disable-next-line no-restricted-globals
        url: `ws://${location.host}/ws`
      }),
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
    this._socket.requestStream({
      data: message,
    }).subscribe({
      onNext: (payload) => this._messageCallback(null, payload.data),
      onError: (error) => this._messageCallback(error),
      onSubscribe: (_subscription) => _subscription.request(MAX_STREAM_ID),
    });
  }
}
