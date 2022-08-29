import React, { FC, memo } from 'react';
import { Route, Routes } from 'react-router-dom';
import { LoginPage } from '../pages/loginPage/loginPage';
import { ProfilePage } from '../pages/profilePage/profilePage';
import { HomePage } from '../pages/homePage/homePage';


const AppRoutes: FC = memo(() => {

  return (
    <Routes>
      <Route path='/' element={<HomePage/>}/>
      <Route path='/login' element={<LoginPage/>}/>
      <Route path='/profile' element={<ProfilePage/>}/>
    </Routes>
  );
});

export default AppRoutes;