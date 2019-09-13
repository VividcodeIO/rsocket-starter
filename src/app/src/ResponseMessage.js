import React from 'react';
import './App.css';

function ResponseMessage(props) {
  const { message, index } = props;
  return (
    <div key={index} className="message">
      {message.timestamp} - {message.message}
    </div>
  );
}

export default ResponseMessage;
