import React from "react";
import {IUser} from "../../../models/user";
import {Container, Dropdown, Image, Menu} from "semantic-ui-react";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import {ReactComponent as Logo} from "../../../assets/logo.svg";
import {NavLink} from "react-router-dom";
import "./AppNavBar.scss";

export type AppNavBarProps = {
    user?: IUser;
}

export const AppNavBar = ({user}: AppNavBarProps) => {
    return (
        <div className="app-navbar-wrapper">
            <Menu fixed="top" className="app-navbar" text>
                <Container>
                    <Menu.Item header as={NavLink} exact to="/">
                        <Logo className="app-navbar__logo"/>
                    </Menu.Item>
                    {user && (
                        <Menu.Item position="right" className="app-navbar__profile">
                            <Dropdown
                                trigger={
                                    <React.Fragment>
                                        <span className="app-navbar__profile-name">{user.firstName}</span>
                                        <Image avatar src={AvatarNotFound}/>
                                    </React.Fragment>
                                }
                                className="app-navbar__dropdown"
                                pointing="top left"
                            >
                                <Dropdown.Menu>
                                    <Dropdown.Item text="Выйти"/>
                                </Dropdown.Menu>
                            </Dropdown>
                        </Menu.Item>
                    )}
                </Container>
            </Menu>
        </div>
    );
};