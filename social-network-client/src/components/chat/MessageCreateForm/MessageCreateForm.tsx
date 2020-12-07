import React, {useContext, useState} from 'react';
import {Form, Icon} from "semantic-ui-react";
import {InputOnChangeData} from "semantic-ui-react/dist/commonjs/elements/Input/Input";
import "./MessageCreateForm.scss";
import {RootStoreContext} from "../../../stores/rootStore";

export interface MessageCreateForm {
    recipientId: string
}

export const MessageCreateForm = ({recipientId}: MessageCreateForm) => {
    const [messageValue, setMessageValue] = useState("");
    const rootStore = useContext(RootStoreContext);
    const {
        sendMessage
    } = rootStore.chatStore;

    const onCommentChange = (_: React.ChangeEvent<HTMLInputElement>, data: InputOnChangeData) => {
        setMessageValue(data.value);
    }

    const handleSubmit = async () => {
        if (messageValue) {
            sendMessage(messageValue, recipientId);
            setMessageValue("");
        }
    }

    return (
        <Form className="post__message" onSubmit={handleSubmit}>
            <Form.Group widths="equal">
                <Form.Input
                    value={messageValue}
                    onChange={onCommentChange}
                    placeholder="Написать сообщение..."
                />
                <button className="button">
                    <Icon link={!!messageValue} className={!messageValue ? "disabled" : ""} name="send" size="large"/>
                </button>
            </Form.Group>
        </Form>
    );
};