import React, { FC, memo, useEffect } from 'react';
import styles from './styles.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleExclamation, faCircleQuestion, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FormProvider, SubmitHandler, useForm } from 'react-hook-form';
import InputField from '../loginRegisterFields/inputField/inputField';
import { Gender, IRegisterData } from '../../interfaces/interfaces';
import { yupResolver } from '@hookform/resolvers/yup';
import { RegisterValidation } from '../../validation/validation';
import RadioField from '../loginRegisterFields/radioField/radioField';
import SelectField from '../loginRegisterFields/selectField/selectField';
import { useAppDispatch } from '../../hooks/storeHooks';
import { registerModalCondition } from '../../store/registerModalSlice/registerModalSlice';
import { currentMonthName, getDaysArray, getMonthNames, getMonthNumber } from '../../utils/dateFormat';


const Register: FC = memo(() => {
  const dispatch = useAppDispatch();
  const methods = useForm<IRegisterData>({
    mode: 'onChange',
    resolver: yupResolver(RegisterValidation),
    defaultValues: {
      name: '',
      lastName: '',
      email: '',
      password: '',
      gender: Gender,
      bYear: new Date().getFullYear(),
      bMonth: currentMonthName(),
      bDay: new Date().getDate(),
    },
  });
  const tempYear = new Date().getFullYear()
  const years: number[] = Array.from(new Array(108), (value, index) => tempYear - index);
  const months: number[] = Array.from(new Array(12), (value, index) => index)
  let days: number[] = getDaysArray(methods.getValues('bYear'), methods.getValues('bMonth'), methods.getValues('bDay'))

  useEffect(() => {
    if (methods.watch('bDay') || methods.watch('bMonth') || methods.watch('bYear'))
      days = getDaysArray(methods.getValues('bYear'), methods.getValues('bMonth'), methods.getValues('bDay'))
  }, [methods.watch('bDay'), methods.watch('bMonth'), methods.watch('bYear')])

  const onSubmit: SubmitHandler<IRegisterData> = (data) => {
    const name = data.name.charAt(0).toUpperCase() + data.name.substring(1)
    const lastName = data.lastName.charAt(0).toUpperCase() + data.lastName.substring(1)
    const year: number = new Date(data.bYear).getFullYear()
    const newUser = {
      name: name,
      lastName: lastName,
      email: data.email,
      password: data.password,
      gender: data.gender,
      birthday: year + '-' + getMonthNumber(data.bMonth, data.bDay, year) + '-' + data.bDay
    }
    console.log(newUser);
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
                <label>Gender <FontAwesomeIcon icon={faCircleQuestion} className={styles.info}/></label>
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
                <label>Birthday <FontAwesomeIcon icon={faCircleQuestion} className={styles.info}/></label>
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
                  <SelectField name="bMonth" values={getMonthNames(months)}/>
                  <SelectField name="bDay" values={days}/>
                  <SelectField name="bYear" values={years}/>
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