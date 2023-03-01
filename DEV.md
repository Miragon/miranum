# Getting Started

Miranum Connect is powered by maven. To set up the moduls run `mvn build`.

## Testing

Start the example projects.

## Publishing
If you want to publish a new Snapshot-Release, you can skip step 1 & 2 and overwrite the current snapshot version.

1. Increase Maven version
    ``` bash
   mvn versions:set -DnewVersion="0.1.0"
   mvn versions:commit
   git commit -a -m "release 0.1.0"
   git push
   ```
2. Create a new pull request and get an approval
3. As a developer of Miragon GmbH, you can access the repos actions and publish a new release by clicking "Run Workflow" in the [release section](https://github.com/FlowSquad/miranum/actions/workflows/release.yaml). Please enter the new version number as release tag.
