import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { ErrorMessage } from '@hookform/error-message';
import { FieldErrors } from 'react-hook-form';

interface Props {
  isDesktop: boolean
  bottom?: boolean
  name: string
  errors: FieldErrors
}

const InputErrorText: FC<Props> = memo(({isDesktop, bottom = false, name, errors}) => {

  return (
    <div className={`${isDesktop && styles.inputErrorDesktop} ${styles.inputError}`}>
      <ErrorMessage name={name} errors={errors} render={({message}) => <p>{message}</p>}/>
      <div className={`${isDesktop ? styles.errorArrowLeft : bottom ? styles.errorArrowBottom : styles.errorArrowTop}`} />
    </div>
  );
});

export default InputErrorText;