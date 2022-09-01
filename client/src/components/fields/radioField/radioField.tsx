import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { useFormContext } from 'react-hook-form';
import { Gender } from '../../../interfaces/interfaces';


interface Props {
  name: string;
  value: Gender
  label: string
  bottom: boolean;
}

const RadioField: FC<Props> = memo(({name, value, label}) => {
  const {register, formState: {errors, isDirty}} = useFormContext();


  return (
    <div className={styles.container}>
        <label htmlFor={label} className={`${errors[name] && styles.fieldError} ${styles.field}`}>
          {label}
          <input type="radio" id={label} value={value} {...register(name)}/>
        </label>
    </div>
  );
});

export default RadioField;