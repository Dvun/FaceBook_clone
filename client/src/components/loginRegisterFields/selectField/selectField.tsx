import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { useFormContext } from 'react-hook-form';


interface Props {
  name: string;
  values: number[] | string[]
}

const SelectField: FC<Props> = memo(({name, values}) => {
  const {register, formState: {errors, isDirty}} = useFormContext();

  return (
    <div className={styles.container}>
      <select {...register(name)} id={name} className={`${isDirty && errors[name] && styles.errorField} ${styles.field}`}>
        {
          values.map((value, index) => (
            <option key={index} value={value}>{value}</option>
          ))
        }
      </select>
    </div>
  );
});

export default SelectField;