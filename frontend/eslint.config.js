import { fileURLToPath } from 'node:url';

import { includeIgnoreFile } from '@eslint/compat';
import js from '@eslint/js';
import prettier from 'eslint-config-prettier';
import importPlugin from 'eslint-plugin-import';
import jsxA11y from 'eslint-plugin-jsx-a11y';
import react from 'eslint-plugin-react';
import reactHooks from 'eslint-plugin-react-hooks';
import svelte from 'eslint-plugin-svelte';
import globals from 'globals';
import ts from 'typescript-eslint';

const gitignorePath = fileURLToPath(new URL('./.gitignore', import.meta.url));

const airbnbRules = {
	'no-console': 'warn',
	'no-debugger': 'error',
	'no-unused-vars': 'warn',
	'prefer-const': 'error',
	'no-var': 'error',
	'object-shorthand': 'error',
	'prefer-template': 'error',
	'template-curly-spacing': 'error',
	'arrow-spacing': 'error',
	'prefer-arrow-callback': 'error',
	'prefer-destructuring': [
		'error',
		{
			array: true,
			object: true
		},
		{
			enforceForRenamedProperties: false
		}
	],
	'no-duplicate-imports': 'error',
	'no-useless-rename': 'error',
	'object-curly-spacing': ['error', 'always'],
	'array-bracket-spacing': ['error', 'never'],
	'comma-dangle': ['error', 'never'],
	quotes: ['error', 'single', { avoidEscape: true }],
	semi: ['error', 'always'],
	indent: ['error', 'tab'],
	'max-len': [
		'warn',
		{ code: 120, ignoreUrls: true, ignoreStrings: true, ignoreTemplateLiterals: true }
	],
	'no-trailing-spaces': 'error',
	'eol-last': 'error',
	'no-multiple-empty-lines': ['error', { max: 1 }],
	'space-before-function-paren': ['warn', 'always'],
	'keyword-spacing': 'error',
	'space-infix-ops': 'error',
	'space-before-blocks': 'error',
	'brace-style': ['error', '1tbs'],
	curly: ['error', 'all'],
	eqeqeq: ['error', 'always'],
	'no-eval': 'error',
	'no-implied-eval': 'error',
	'no-new-func': 'error',
	'no-script-url': 'error',
	'no-sequences': 'error',
	'no-throw-literal': 'error',
	'no-unmodified-loop-condition': 'error',
	'no-unused-expressions': 'error',
	'no-useless-call': 'error',
	'no-useless-concat': 'error',
	'no-useless-return': 'error',
	'prefer-promise-reject-errors': 'error',
	radix: 'error',
	'wrap-iife': ['error', 'outside'],
	yoda: 'error',

	'import/order': 'off',
	'import/no-unresolved': 'off',
	'import/extensions': 'off',
	'import/no-extraneous-dependencies': [
		'error',
		{
			devDependencies: true,
			optionalDependencies: false,
			peerDependencies: false
		}
	],

	'@typescript-eslint/no-unused-vars': 'warn',
	'@typescript-eslint/explicit-function-return-type': 'off',
	'@typescript-eslint/explicit-module-boundary-types': 'off',
	'@typescript-eslint/no-explicit-any': 'warn',
	'@typescript-eslint/prefer-nullish-coalescing': 'off',
	'@typescript-eslint/prefer-optional-chain': 'off',
	'@typescript-eslint/no-unnecessary-type-assertion': 'off',
	'@typescript-eslint/no-floating-promises': 'off',
	'@typescript-eslint/await-thenable': 'off',
	'@typescript-eslint/no-misused-promises': 'off'
};

export default ts.config(
	includeIgnoreFile(gitignorePath),
	js.configs.recommended,
	...ts.configs.recommended,
	...svelte.configs['flat/recommended'],
	prettier,
	...svelte.configs['flat/prettier'],
	{
		languageOptions: {
			globals: {
				...globals.browser,
				...globals.node
			}
		},
		plugins: {
			import: importPlugin,
			'jsx-a11y': jsxA11y,
			react,
			'react-hooks': reactHooks
		},
		rules: airbnbRules
	},
	{
		files: ['**/*.svelte'],
		languageOptions: {
			parserOptions: {
				parser: ts.parser
			}
		},
		rules: {
			'prefer-const': 'off',
			'no-unused-vars': 'off',
			'@typescript-eslint/no-unused-vars': 'off',
			'space-before-function-paren': 'off',
			'max-len': 'off',
			'no-console': 'off',
			'svelte/valid-compile': 'off'
		}
	}
);
