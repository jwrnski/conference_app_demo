/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Thymeleaf HTML templates path
    './src/**/*.java',
    './src/**/*.{js,ts,jsx,tsx}'// Include paths for any Java class embedding Tailwind classes
  ],
  theme: {
    extend: {
      fontFamily: {
        atkinson: ['Atkinson Hyperlegible', 'sans-serif'],
      },
    },
  },
  plugins: [],
};
