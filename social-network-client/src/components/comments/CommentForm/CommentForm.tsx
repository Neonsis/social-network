import React, {useContext, useState} from "react";
import {Form, Icon} from "semantic-ui-react";
import {InputOnChangeData} from "semantic-ui-react/dist/commonjs/elements/Input/Input";
import "./CommentForm.scss";
import {RootStoreContext} from "../../../stores/rootStore";

export interface CommentFormProps {
    postId: string;
}

export const CommentForm = ({postId}: CommentFormProps) => {
    const rootStore = useContext(RootStoreContext);
    const {
        addComment
    } = rootStore.postStore;
    const [commentValue, setCommentValue] = useState("");

    const onCommentChange = (_: React.ChangeEvent<HTMLInputElement>, data: InputOnChangeData) => {
        setCommentValue(data.value);
    }

    const handleSubmit = async () => {
        if (commentValue) {
            addComment(postId, {content: commentValue});
            setCommentValue("");
        }
    }

    return (
        <Form className="post__comment" onSubmit={handleSubmit}>
            <Form.Group widths="equal">
                <Form.Input
                    value={commentValue}
                    onChange={onCommentChange}
                    placeholder="Написать комментарий..."
                />
                <button className="button">
                    <Icon link={!!commentValue} className={!commentValue ? "disabled" : ""} name="send" size="large"/>
                </button>
            </Form.Group>
        </Form>
    );
};