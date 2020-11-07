import React from 'react';
import {Button, Form, Segment} from "semantic-ui-react";

export const SignInForm = () => {
    return (
        <Segment className="sign-in">
            <Form>
                <Form.Field>
                    <input placeholder='Email'/>
                </Form.Field>
                <Form.Field>
                    <input placeholder='Password'/>
                </Form.Field>
                <Button type="submit" className="primary-button">Login</Button>
            </Form>
        </Segment>
    );
};