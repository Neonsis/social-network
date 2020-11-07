import React, {useState} from "react";
import {Button, Form, Header, Radio, Segment} from "semantic-ui-react"
import SemanticDatepicker from "react-semantic-ui-datepickers";
import "./SignUpForm.scss";

export const SignUpForm = () => {

    const [gender, setGender] = useState<string>();

    const handleGenderChange = (event: React.ChangeEvent<HTMLInputElement>, {value}: any) => {
        setGender(value);
    }

    return (
        <Segment className="sign-up">
            <Header as="h3" textAlign="center">
                First time here?
                <Header.Subheader>
                    Sign up for VK
                </Header.Subheader>
            </Header>
            <Form>
                <Form.Field>
                    <input placeholder="Your first name"/>
                </Form.Field>
                <Form.Field>
                    <input placeholder="Your last name"/>
                </Form.Field>
                <Form.Field className="sign-up__date-picker">
                    <label>Birthday</label>
                    <SemanticDatepicker/>
                </Form.Field>
                <Form.Field>
                    <label>Birthday</label>
                    <Form.Group inline>
                        <Form.Field
                            control={Radio}
                            label="One"
                            value="1"
                            checked={gender === "1"}
                            onChange={handleGenderChange}
                        />
                        <Form.Field
                            control={Radio}
                            label="Two"
                            value="2"
                            checked={gender === "2"}
                            onChange={handleGenderChange}
                        />
                    </Form.Group>
                </Form.Field>
                <Button type="submit" className="register-button" fluid>Sign Up</Button>
            </Form>
        </Segment>
    );
};