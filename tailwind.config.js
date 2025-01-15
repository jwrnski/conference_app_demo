/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Thymeleaf HTML templates path
    './src/**/*.java', // Include paths for any Java class embedding Tailwind classes
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};
