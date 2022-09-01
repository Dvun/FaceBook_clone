import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { useFormContext } from 'react-hook-form';


interface Props {
  name: string;
  value: string
}

const SelectField: FC<Props> = memo(({name, value}) => {
  const {register, formState: {errors, isDirty}} = useFormContext();

  return (
    <div className={styles.container}>
      <select defaultValue='' {...register(name)} id={name} className={`${isDirty && errors[name] && styles.errorField} ${styles.field}`}>
        <option value="none" disabled hidden></option>
        <option value="11">{value}</option>
      </select>
    </div>
  );
});

export default SelectField;