import React from "react";
import {Card} from "semantic-ui-react";
import "./CommentsSection.scss";
import {CommentForm} from "../CommentForm";
import {IComment, ICommentFormValues} from "../../../models/post";
import {CommentsList} from "../CommentsList";

export interface CommentsSectionProps {
    postId: string;
    comments: IComment[];
    addComment: (postId: string, values: ICommentFormValues) => void;
}

export const CommentsSection = ({postId, comments, addComment}: CommentsSectionProps) => {
    return (
        <Card.Content extra>
            <CommentsList comments={comments}/>
            <CommentForm postId={postId} addComment={addComment}/>
        </Card.Content>
    );
};