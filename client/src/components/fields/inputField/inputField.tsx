import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { useFormContext } from 'react-hook-form';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleExclamation } from '@fortawesome/free-solid-svg-icons';
import { useMediaQuery } from 'react-responsive';
import InputErrorText from '../../inputErrorText/inputErrorText';


interface Props {
  name: string;
  type: string;
  placeholder: string;
  bottom: boolean;
}

const InputField: FC<Props> = memo(({name, type, placeholder, bottom}) => {
  const {register, formState: {errors, isDirty}} = useFormContext();
  const isDesktop = useMediaQuery({minWidth: 850});


  return (
    <div className={styles.container}>
      {isDirty && errors[name] && !bottom &&
        <InputErrorText isDesktop={isDesktop} name={name} errors={errors}/>
      }

      <input
        type={type}
        placeholder={placeholder}
        {...register(name)}
        className={`${isDirty && errors[name] && styles.error}`}
      />

      {isDirty && errors[name] && bottom &&
        <InputErrorText isDesktop={isDesktop} name={name} errors={errors} bottom={bottom}/>
      }

      {isDirty && errors[name] &&
        <FontAwesomeIcon
          icon={faCircleExclamation}
          style={{top: `${!bottom && !isDesktop ? '60%' : '15px'}`}}
          color="#b94a48"
        />
      }
    </div>
  );
});

export default InputField;