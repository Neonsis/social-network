import React, {useState} from "react";
import {Button, Form, Message, Segment} from "semantic-ui-react";
import {useForm} from "react-hook-form";
import {IUserFormValues} from "../../../models/user";
import {user} from "../../../api/agent"

export const SignInForm = () => {
    const {register, handleSubmit, errors} = useForm<IUserFormValues>();
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<boolean>(false);

    const onSubmit = (values: IUserFormValues) => {
        setError(false);
        setLoading(true);
        user.login(values)
            .then(value => console.log(value))
            .catch(() => setError(true))
            .finally(() => setLoading(false))
    }

    return (
        <Segment className="sign-in">
            <Form onSubmit={handleSubmit(onSubmit)} error={error}>
                <Form.Field error={errors.email}>
                    <input
                        name="email"
                        placeholder="Email"
                        ref={register({required: true})}
                    />
                </Form.Field>
                <Form.Field error={errors.password}>
                    <input
                        name="password"
                        type="password"
                        placeholder="Password"
                        ref={register({required: true})}
                    />
                </Form.Field>
                <Message
                    error
                    header='Action Forbidden'
                    content="Invalid email or password"
                />
                <Button
                    type="submit"
                    className="primary-button"
                    loading={loading}
                >
                    Login
                </Button>
            </Form>
        </Segment>
    );
};