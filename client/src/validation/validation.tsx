import * as yup from 'yup';
import { Gender } from '../interfaces/interfaces';

export const loginValidation = yup.object({
  email: yup.string().matches(/[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/, 'Must be a valid email!').required('Email address is required!').trim(),
  password: yup.string().required('Password is required!').matches(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$/, 'Minimum 6 characters, at least one upper case, one lower case, one number!')
})

export const RegisterValidation = yup.object({
  name: yup.string().required().trim(),
  lastName: yup.string().required('Last name is required field!').trim(),
  email: yup.string().matches(/[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/, 'Must be a valid email!').required('Email address is required!').trim(),
  password: yup.string().required('Password is required!').matches(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$/, 'Minimum 6 characters, at least one upper case, one lower case, one number!'),
  gender: yup.mixed().oneOf(Object.values(Gender)),
  bYear: yup.date().required().max(new Date((new Date().getFullYear() - 5), 0, 1)),
  bMonth: yup.string().required().trim(),
  bDay: yup.string().required().trim(),
})