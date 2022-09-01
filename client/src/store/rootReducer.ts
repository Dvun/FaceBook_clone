import { combineReducers } from '@reduxjs/toolkit';
import registerModalSlice from './registerModalSlice/registerModalSlice';


export const rootReducer = combineReducers({
  registerModal: registerModalSlice
})

