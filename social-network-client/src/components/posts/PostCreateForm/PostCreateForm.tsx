import React, {useContext, useState} from 'react';
import "./PostCreateForm.scss";
import {Button, Form, Segment} from "semantic-ui-react";
import {observer} from "mobx-react-lite";
import {RootStoreContext} from "../../../stores/rootStore";

export const PostCreateForm = observer(() => {
    const [content, setContent] = useState("");
    const rootStore = useContext(RootStoreContext);
    const {
        saveLoadingPost,
        createPost,
        userPosts
    } = rootStore.postStore;

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setContent(event.target.value);
    }

    const handleSubmit = () => {
        if (content) {
            createPost({content})
        }
    }

    return (
        <Segment>
            <Form className="post__form" onSubmit={handleSubmit}>
                <Form.Group widths='equal'>
                    <Form.Input
                        value={content}
                        placeholder='Что у вас нового?'
                        onChange={handleChange}
                    />
                    <Button
                        className="primary-button"
                        loading={saveLoadingPost}
                        disabled={saveLoadingPost}
                    >
                        Отправить
                    </Button>
                </Form.Group>
            </Form>
        </Segment>
    );
});