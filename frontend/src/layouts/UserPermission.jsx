import React from 'react';

const UserForm = ({ children }) => {
  return (
    <div className="flex items-center justify-center w-screen min-h-[calc(100vh-100px)] bg-white overflow-hidden">
      <div className="bg-white p-2 rounded max-w-screen-md w-full">
        {children}
      </div>
    </div>
  );
};

export default UserForm;