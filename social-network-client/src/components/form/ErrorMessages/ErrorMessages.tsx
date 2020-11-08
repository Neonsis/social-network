import React from "react";
import {AxiosResponse} from "axios";
import {Message} from "semantic-ui-react";
import "./ErrorMessages.scss";

interface ResponseData {
    message: string;
    details: Details;
}

interface Details {
    field: string;
    message: string;
}

interface ErrorMessagesProps {
    error: AxiosResponse<ResponseData>;
    text?: string;
}

export const ErrorMessages = ({error, text}: ErrorMessagesProps) => {
    return (
        <Message error className="error-messages">
            <Message.Header>{error.data.message}</Message.Header>
            {error.data && Object.keys(error.data.details).length > 0 && (
                <Message.List>
                    {Object.values(error.data.details).map((error, i) => (
                        <Message.Item key={i + error.field}>{error.message}</Message.Item>
                    ))}
                </Message.List>
            )}
            {text && <Message.Content content={text}/>}
        </Message>
    );
};