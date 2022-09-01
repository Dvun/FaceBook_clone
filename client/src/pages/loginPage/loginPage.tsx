import React, { FC, memo, useState } from 'react';
import Login from '../../components/login/login';
import Register from '../../components/register/Register';


export const LoginPage: FC = memo(() => {
  const [register, setRegister] = useState<boolean>(false)


  return (
    <>
      <Login setRegister={setRegister}/>
      {
        register && <Register setRegister={setRegister}/>
      }
    </>
  );
});