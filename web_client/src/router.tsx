import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Users from './pages/Users';
import Main from './pages/Main';
import MainLayout from './layouts/Main';
import NotFound from "./pages/NotFound";

const Router = () => (
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<MainLayout />}>
                <Route index element={<Main />} />
                <Route path="users" element={<Users />} />
                <Route path="*" element={<NotFound />} />
            </Route>
        </Routes>
    </BrowserRouter>
)

export default Router;
