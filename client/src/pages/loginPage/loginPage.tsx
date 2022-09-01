import React, { FC, memo } from 'react';
import Login from '../../components/login/login';
import Register from '../../components/register/Register';
import { useAppSelector } from '../../hooks/storeHooks';


export const LoginPage: FC = memo(() => {
  const {isOpenRegister} = useAppSelector(state => state.registerModal)


  return (
    <>
      <Login/>
      {isOpenRegister && <Register/>}
    </>
  );
});