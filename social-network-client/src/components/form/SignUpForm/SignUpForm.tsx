import React, {SyntheticEvent, useContext, useEffect, useState} from "react";
import {Button, DropdownProps, Form, Header, Segment} from "semantic-ui-react"
import SemanticDatepicker from "react-semantic-ui-datepickers";
import "./SignUpForm.scss";
import {useForm} from "react-hook-form";
import {IUserFormValues} from "../../../models/user";
import {ErrorMessages} from "../index";
import {AxiosResponse} from "axios";
import {SemanticDatepickerProps} from "react-semantic-ui-datepickers/dist/types";
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";

const options = [
    {key: "m", text: "Male", value: "MALE"},
    {key: "f", text: "Female", value: "FEMALE"}
]

export const SignUpForm = observer(() => {
    const rootStore = useContext(RootStoreContext);
    const {register: registerRequest, loading} = rootStore.userStore;
    const {register, handleSubmit, errors, setValue, trigger} = useForm<IUserFormValues>();
    const [error, setError] = useState<boolean>(false);
    const [apiError, setApiError] = useState<AxiosResponse>();

    useEffect(() => {
        register({name: "gender"}, {required: true});
        register({name: "birthday"}, {required: true});
    }, []);

    const onSubmit = (values: IUserFormValues) => {
        setError(false);
        registerRequest(values)
            .catch((error) => setApiError(error))
    }

    const onGenderChange = async (e: SyntheticEvent<HTMLElement>, {name, value}: DropdownProps) => {
        setValue(name, value);
        await trigger(name);
    }

    const onBirthdayChange = async (event: SyntheticEvent<Element, Event> | undefined, data: SemanticDatepickerProps) => {
        setValue("birthday", data.value);
        await trigger("birthday");
    }

    return (
        <Segment className="sign-up">
            <Header as="h3" textAlign="center">
                Sign up for VK
            </Header>
            <Form onSubmit={handleSubmit(onSubmit)} error={error || apiError !== undefined}>
                <Form.Field error={errors.firstName}>
                    <input
                        name="firstName"
                        placeholder="Your first name"
                        ref={register({required: true})}
                    />
                </Form.Field>
                <Form.Field error={errors.lastName}>
                    <input
                        name="lastName"
                        placeholder="Your last name"
                        ref={register({required: true})}
                    />
                </Form.Field>
                <Form.Field error={errors.email}>
                    <input
                        name="email"
                        placeholder="Your email"
                        ref={register({required: true})}
                    />
                </Form.Field>
                <Form.Field error={errors.password}>
                    <input
                        name="password"
                        placeholder="Your password"
                        type="password"
                        ref={register({required: true})}
                    />
                </Form.Field>
                <Form.Field className="sign-up__date-picker" error={errors.birthday}>
                    <label>Birthday</label>
                    <SemanticDatepicker
                        name="birthday"
                        onChange={onBirthdayChange}
                    />
                </Form.Field>
                <Form.Field error={errors.gender}>
                    <Form.Select
                        name="gender"
                        options={options}
                        placeholder="Gender"
                        onChange={onGenderChange}
                    />
                </Form.Field>
                {apiError && <ErrorMessages error={apiError}/>}
                <Button
                    type="submit"
                    className="register-button"
                    fluid
                    loading={loading}
                    disabled={loading}
                >
                    Sign Up
                </Button>
            </Form>
        </Segment>
    );
});