import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import InputField from '../fields/inputField/inputField';
import { Link } from 'react-router-dom';
import { ILoginData } from '../../interfaces/interfaces';
import { yupResolver } from '@hookform/resolvers/yup';
import { loginValidation } from '../../validation/validation';


interface Props {
  setRegister: (bool: boolean) => void
}

const Login: FC<Props> = memo(({setRegister}) => {
  const methods = useForm<ILoginData>({
    mode: 'onChange',
    defaultValues: {email: '', password: ''},
    resolver: yupResolver(loginValidation),
  });

  const onSubmit: SubmitHandler<ILoginData> = (data) => {
    console.log(data);
  };

  return (
    <div className={styles.container}>
      <div className={styles.loginWrapper}>
        <div className={styles.login1}>
          <img src="./icons/facebook.svg" alt="facebook"/>
          <span>Facebook clone helps you connect and share with the people in your life.</span>
        </div>
        <div className={styles.login2}>
          <div className={styles.login2Wrap}>
            <FormProvider {...methods}>
              <form onSubmit={methods.handleSubmit(onSubmit)}>
                <InputField name="email" type="text" placeholder="Email address" bottom={false}/>
                <InputField name="password" type="password" placeholder="Password" bottom={true}/>
                <button type="submit" className={`blue_btn`}>Log In</button>
              </form>
            </FormProvider>
            <Link to="/forgot">Forgotten password?</Link>
            <hr/>
            <button className={`blue_btn ${styles.openSignup}`} onClick={() => setRegister(true)}>Create Account</button>
          </div>
          <Link to="/"><b>Create a Page </b> for a celebrity, brand or business.</Link>
        </div>
        <div className={styles.registerButton}></div>
      </div>
    </div>
  );
});

export default Login;