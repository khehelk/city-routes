import React from 'react'

interface InputProps {
    type?: React.HTMLInputTypeAttribute;
    value: number | string;
    onChange: (value: number | string) => void;
    placeholder?: string;
    className?: string;
}

const Input = (props: InputProps) => {
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const inputValue = event.target.value;

        if (props.type === 'number') {
            if (inputValue === '') {
                props.onChange('');
                return;
            }
            const newValue = Number(inputValue);
            if (!isNaN(newValue)) {
                props.onChange(newValue);
            }
            return;
        }
        props.onChange(inputValue);
    };

    return (
        <input
            type={props.type}
            value={props.value ?? ''}
            onChange={handleChange}
            placeholder={props.placeholder}
            className={`w-full p-3 text-gray-700 border outline-none focus:ring-2 ${props.className}`}
        />
    );
}
export default Input
