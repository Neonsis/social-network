import React, {ChangeEvent, useContext, useState} from "react";
import "./PostCreateForm.scss";
import {Button, Form, Segment, TextAreaProps} from "semantic-ui-react";
import {observer} from "mobx-react-lite";
import {RootStoreContext} from "../../../stores/rootStore";

export const PostCreateForm = observer(() => {
    const [content, setContent] = useState("");
    const rootStore = useContext(RootStoreContext);
    const {
        saveLoadingPost,
        createPost
    } = rootStore.postStore;

    const handleChange = (event: ChangeEvent<HTMLTextAreaElement>, data: TextAreaProps) => {
        setContent(data.value!.toString());
    }

    const handleSubmit = () => {
        if (content) {
            createPost({content})
            setContent("");
        }
    }

    return (
        <Segment>
            <Form className="post__form" onSubmit={handleSubmit}>
                <Form.Group widths="equal">
                    <Form.TextArea onChange={handleChange} placeholder='Что у вас нового?'/>
                    <Button
                        className="primary-button"
                        loading={saveLoadingPost}
                        disabled={saveLoadingPost}
                    >
                        Опубликовать
                    </Button>
                </Form.Group>
            </Form>
        </Segment>
    );
});