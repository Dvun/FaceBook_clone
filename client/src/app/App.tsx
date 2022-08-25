import React from 'react';
import './App.scss';

function App() {
  return (
    <div style={{marginLeft: '50px', marginTop: '50px'}}>
      <div><b>Welcome!</b></div>
      <br/>
      <div>Please activate your account using the link within 30 minutes!</div>
      <br/>
      <br/>
      <a
        href="${url}"
        style={{
          width: '200px',
          height: '50px',
          backgroundColor: 'blue',
          textDecoration: 'none',
          color: 'white',
          padding: '13px 45px',
          borderRadius: '10px',
          fontSize: 18
      }}>
        Activate
      </a>
      <br/>
      <br/>
    </div>
  );
}

export default App;
