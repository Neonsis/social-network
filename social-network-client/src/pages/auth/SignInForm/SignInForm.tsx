import React, {useContext, useState} from "react";
import {Button, Form, Message, Segment} from "semantic-ui-react";
import {useForm} from "react-hook-form";
import {IUserFormValues} from "../../../models/user";
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";

export const SignInForm = observer(() => {
    const rootStore = useContext(RootStoreContext);
    const {login, loading} = rootStore.userStore;
    const {register, handleSubmit, errors} = useForm<IUserFormValues>();
    const [error, setError] = useState<boolean>(false);

    const onSubmit = (values: IUserFormValues) => {
        setError(false);
        login(values)
            .catch(() => setError(true))
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
                    header="Action Forbidden"
                    content="Invalid email or password"
                />
                <Button
                    type="submit"
                    className="primary-button"
                    loading={loading}
                    disabled={loading}
                >
                    Login
                </Button>
            </Form>
        </Segment>
    );
});