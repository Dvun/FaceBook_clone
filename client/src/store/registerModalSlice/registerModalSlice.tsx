import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface IInitialState {
  isOpenRegister: boolean
}

const initialState: IInitialState = {
  isOpenRegister: false
}

const registerModalSlice = createSlice({
  name: 'registerModal',
  initialState: initialState,
  reducers: {

    registerModalCondition: (state, action: PayloadAction<boolean>) => {
      state.isOpenRegister = action.payload
    }

  }
})


export default registerModalSlice.reducer
export const {
  registerModalCondition
} = registerModalSlice.actions