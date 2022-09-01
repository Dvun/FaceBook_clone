import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { ErrorMessage } from '@hookform/error-message';
import { FieldErrors } from 'react-hook-form';
import { useAppSelector } from '../../hooks/storeHooks';

interface Props {
  isDesktop: boolean
  bottom?: boolean
  name: string
  errors: FieldErrors
}

const InputErrorText: FC<Props> = memo(({isDesktop, bottom = false, name, errors}) => {
  const {isOpenRegister} = useAppSelector(state => state.registerModal)

  function errorArrow() {
    if (isDesktop && name === 'lastName') return styles.errorArrowRight
    if (isDesktop) return styles.errorArrowLeft
    if (bottom) return styles.errorArrowBottom
    return styles.errorArrowTop
  }

  function inputError() {
    if (!isDesktop) return styles.inputError
    if (name === 'lastName') return styles.inputErrorLastname + ' ' + styles.inputError
    if (isDesktop) return styles.inputErrorDesktop + ' ' + styles.inputError
  }

  function inputErrorRight() {
    if (isOpenRegister && name === 'name' && isDesktop) return '13rem'
    if (isOpenRegister && isDesktop) return '26rem'
    if (!isOpenRegister && isDesktop) return '24rem'
    return '0'
  }

  return (
    <div className={inputError()} style={{right: inputErrorRight()}}>
      <ErrorMessage name={name} errors={errors} render={({message}) => <p>{message}</p>}/>
      <div className={errorArrow()} />
    </div>
  );
});

export default InputErrorText;