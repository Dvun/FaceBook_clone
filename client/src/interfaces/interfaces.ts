export interface IColor {
  color: string
}

export interface ILoginData {
  email: string
  password: string
}

export enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export interface IRegisterData {
  name: string,
  lastName: string,
  email: string,
  password: string,
  gender: typeof Gender,
  bYear: string,
  bMonth: string,
  bDay: string
}