import React, {useState} from "react";
import {Form, Icon} from "semantic-ui-react";
import {InputOnChangeData} from "semantic-ui-react/dist/commonjs/elements/Input/Input";
import "./CommentForm.scss";
import {ICommentFormValues} from "../../../models/post";

export interface CommentFormProps {
    postId: string;
    addComment: (postId: string, values: ICommentFormValues) => void;
}

export const CommentForm = ({postId, addComment}: CommentFormProps) => {
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