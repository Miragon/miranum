# Using local Node

## Getting started

- `npm run init`
- `npm run build`
- `npm run serve:forms`

## Release

- run `npm run new-version` locally
- check in
- create tag with `app_` prefix in GitHub

## Development

To simplify development we provided a few shortcut commands to rebuild components on change and to run the applications

```bash
# Rebuild components on change
npm run dev

# Run all applications from packages/apps
npm run serve

# Run a specific application from packages/apps
npm run serve:forms
```

The `npm run dev` command is a shortcut for `npx lerna run watch --stream --parallel` command.
If you don't want to watch for changes in all components you can add the `--scope` flag to the command,
e.g. `npx lerna run watch --stream --scope=@muenchen/digiwf-multi-file-input`.

To run the dev servers for the application you can use the `npm run serve` command.
If you don't want to serve all applications with one command you can use the helper commands for each
app `npm run serve:forms`.

# Using Maven

Instead of using a local node installation, you can make Maven to install and run the correct version of node.

To first initialize the node build:

`mvn -Pfrontend clean install`

This will install node and all dependent packages to your machine.

Here are helpful commands:

- `mvn -f apps clean install` rebuilds the apps
- `mvn -f apps -Pserve` serves the apps allowing local development
- `mvn -f apps -Pserve-tasklist` serves the tasklist allowing local development
- `mvn -f apps -Pserve-forms` serves the tasklist allowing local development
- `mvn -f apps -Pclean-modules` enforces the deletion of locally installed modules

