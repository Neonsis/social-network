import React from "react";
import {Card} from "semantic-ui-react";
import "./CommentsSection.scss";
import {CommentForm} from "../CommentForm";
import {IComment} from "../../../models/post";
import {CommentsList} from "../CommentsList";

export interface CommentsSectionProps {
    postId: string;
    comments: IComment[];
}

export const CommentsSection = ({postId, comments}: CommentsSectionProps) => {
    return (
        <Card.Content extra>
            <CommentsList comments={comments}/>
            <CommentForm postId={postId}/>
        </Card.Content>
    );
};