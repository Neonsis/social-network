import React, {useContext} from "react";
import {Icon, Label, Menu} from "semantic-ui-react";
import {RootStoreContext} from "../../../stores/rootStore";
import {Link} from "react-router-dom";
import "./Sidebar.scss";

export const Sidebar = () => {
    const rootStore = useContext(RootStoreContext);
    const {user} = rootStore.userStore;

    return (
        <Menu vertical text className="main-sidebar">
            <Menu.Item as={Link} to={`/id${user!.id}`} className="main-sidebar__item">
                <Icon name="home"/>
                Моя страница
            </Menu.Item>
            <Menu.Item as={Link} to="/feed" className="main-sidebar__item">
                <Icon name="newspaper outline"/>
                Новости
            </Menu.Item>
            <Menu.Item as={Link} to="/messages" className="main-sidebar__item">
                <Icon name="chat"/>
                Мессенджер
                <Label>1</Label>
            </Menu.Item>
            <Menu.Item as={Link} to={`/friends`} className="main-sidebar__item">
                <Icon name="user"/>
                Друзья
            </Menu.Item>
            <Menu.Item as={Link} to="/groups" className="main-sidebar__item">
                <Icon name="users"/>
                Сообщества
            </Menu.Item>
        </Menu>
    );
};