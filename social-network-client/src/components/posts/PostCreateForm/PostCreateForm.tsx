import React, {ChangeEvent, useContext, useState} from "react";
import "./PostCreateForm.scss";
import {Button, Form, Segment, TextAreaProps} from "semantic-ui-react";
import {observer} from "mobx-react-lite";
import {RootStoreContext} from "../../../stores/rootStore";
import {IPost, IPostFormValues} from "../../../models/post";

export interface PostCreateFormProps {
    create: (post: IPostFormValues) => void;
    loading: boolean;
}

export const PostCreateForm = observer(({create, loading}: PostCreateFormProps) => {
    const [content, setContent] = useState("");

    const handleChange = (event: ChangeEvent<HTMLTextAreaElement>, data: TextAreaProps) => {
        setContent(data.value!.toString());
    }

    const handleSubmit = () => {
        if (content) {
            create({content})
            setContent("");
        }
    }

    return (
        <Segment>
            <Form className="post__form" onSubmit={handleSubmit}>
                <Form.Group widths="equal">
                    <Form.TextArea value={content} onChange={handleChange} placeholder='Что у вас нового?'/>
                    <Button
                        className="primary-button"
                        loading={loading}
                        disabled={loading}
                    >
                        Опубликовать
                    </Button>
                </Form.Group>
            </Form>
        </Segment>
    );
});