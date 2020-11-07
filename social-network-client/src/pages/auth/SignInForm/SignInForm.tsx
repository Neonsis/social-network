import React from "react";
import {Button, Form, Segment} from "semantic-ui-react";
import {useForm} from "react-hook-form";
import {IUserFormValues} from "../../../models/user";
import agent from "../../../api/agent"

export const SignInForm = () => {
    const {register, handleSubmit, errors} = useForm<IUserFormValues>();

    const onSubmit = (values: IUserFormValues) => {
        agent.User.login(values);
    }

    return (
        <Segment className="sign-in">
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Field error={errors.email}>
                    <input name="email" placeholder="Email" ref={register({required: true})}/>
                </Form.Field>
                <Form.Field error={errors.password}>
                    <input name="password" placeholder="Password" ref={register({required: true})}/>
                </Form.Field>
                <Button type="submit" className="primary-button">Login</Button>
            </Form>
        </Segment>
    );
};