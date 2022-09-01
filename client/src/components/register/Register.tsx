import React, { FC, memo } from 'react';
import styles from './styles.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleExclamation, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import InputField from '../loginRegisterFields/inputField/inputField';
import { Gender, IRegisterData } from '../../interfaces/interfaces';
import { yupResolver } from '@hookform/resolvers/yup';
import { RegisterValidation } from '../../validation/validation';
import RadioField from '../loginRegisterFields/radioField/radioField';
import SelectField from '../loginRegisterFields/selectField/selectField';
import { useAppDispatch } from '../../hooks/storeHooks';
import { registerModalCondition } from '../../store/registerModalSlice/registerModalSlice';


const Register: FC = memo(() => {
  const dispatch = useAppDispatch()
  const methods = useForm<IRegisterData>({
    mode: 'onChange',
    resolver: yupResolver(RegisterValidation),
    defaultValues: {
      name: '',
      lastName: '',
      email: '',
      password: '',
      gender: Gender,
      bYear: '',
      bMonth: '',
      bDay: '',
    },
  });

  const onSubmit: SubmitHandler<IRegisterData> = (data) => {
    console.log(data);
  };

  return (
    <div className="blur">
      <div className={styles.container}>
        <div className={styles.header}>
          <FontAwesomeIcon icon={faXmark} onClick={() => dispatch(registerModalCondition(false))}/>
          <span>Sign Up</span>
          <span>It's quick and easy</span>
        </div>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(onSubmit)}>
            <div>
              <div className={styles.nameLastnameFields}>
                <InputField name="name" type="text" placeholder="First Name" bottom={false}/>
                <InputField name="lastName" type="text" placeholder="Last Name" bottom={false}/>
              </div>
              <InputField name="email" type="email" placeholder="Email address" bottom={false}/>
              <InputField name="password" type="password" placeholder="New password" bottom={false}/>

              <div className={styles.genderFields}>
                <label>Gender</label>
                {methods.formState.isDirty && methods.formState.errors['gender'] &&
                  <FontAwesomeIcon
                    icon={faCircleExclamation}
                    color="#b94a48"
                  />
                }
                <div className={styles.genderFieldsWrapper}>
                  <RadioField name="gender" label={Gender.MALE} value={Gender.MALE} bottom={false}/>
                  <RadioField name="gender" label={Gender.FEMALE} value={Gender.FEMALE} bottom={false}/>
                </div>
              </div>

              <div className={styles.birthdayFields}>
                <label>Birthday</label>
                {methods.formState.isDirty &&
                  (methods.formState.errors['bMonth'] ||
                    methods.formState.errors['bMonth'] ||
                    methods.formState.errors['bMonth']) &&
                  <FontAwesomeIcon
                    icon={faCircleExclamation}
                    color="#b94a48"
                  />
                }
                <div className={styles.birthdayFieldsWrapper}>
                  <SelectField name="bMonth" value="11"/>
                  <SelectField name="bDay" value="11"/>
                  <SelectField name="bYear" value="1984"/>
                </div>
              </div>

            </div>
            <p className={styles.termsText}>
              By clicking Sign Up, you agree to our Terms. Learn how we collect, use and share your data in our Privacy
              Policy and how we use cookies and similar technology in our Cookies Policy. You may receive SMS
              Notifications from us and can opt out any time.
            </p>
            <div className={styles.buttonWrapper}>
              <button type="submit" className={`blue_btn`}>Sign Up</button>
            </div>
          </form>
        </FormProvider>
      </div>
    </div>
  );
});

export default Register;