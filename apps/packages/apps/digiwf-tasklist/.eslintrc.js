module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    "plugin:@typescript-eslint/recommended",
    'plugin:vue/essential',
    "plugin:vue/strongly-recommended",
    "plugin:vue/recommended",
    'eslint:recommended',
    '@vue/typescript'
  ],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    semi: [2, "always"]
  },
  parser: "vue-eslint-parser",
  parserOptions: {
    parser: '@typescript-eslint/parser'
  },
  overrides: [
    {
      files: [
        '**/__tests__/*.{j,t}s?(x)',
        '**/tests/unit/**/*.spec.{j,t}s?(x)'
      ],
      rules: {
        "@typescript-eslint/no-explicit-any": "off"
      },
      env: {
        jest: true
      }
    }
  ]
};
