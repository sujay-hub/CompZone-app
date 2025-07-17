import React from 'react'

const useAuth = () => {
        const userId = localStorage.getItem("userId");
        const token = localStorage.getItem("token");
        return {userId, token};
};

export default useAuth