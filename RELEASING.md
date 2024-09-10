# Releasing
Releases happen automatically every week. If you need to make a manual release, there are 2 ways you can do that: using CircleCI and locally, using Fastlane.

## Using CircleCI
Trigger a pipeline in CircleCI for the branch you want to release (e.g. `main`), and pass a paramater with name `action` and value `bump`.

![Screenshot!](https://user-images.githubusercontent.com/664544/191806930-07737e3d-8c44-4bfd-8cef-b471b72643a4.png "CircleCI screenshot")


The same can be done using the CircleCI API like so:

```shell
curl --location --request POST 'https://circleci.com/api/v2/project/github/RevenueCat/purchases-kmp/pipeline' \
            --header 'Content-Type: application/json' \
            -u "your_circleci_personal_token:" \
            -d '{
              "branch": "main",
              "parameters": {
                "action": "bump"
              }
            }'
```

More information on triggering pipelines can be found in the [CircleCI docs](https://circleci.com/docs/triggers-overview).

## Locally using Fastlane
1. Create a `fastlane/.env` file with your GitHub API token (see `fastlane/.env.SAMPLE`). This will be used to create the PR, so you should use your own token so the PR gets assigned to you.
2. Run `bundle exec fastlane bump github_rate_limit:10`. The `github_rate_limit` argument is optional, but recommended, to avoid hitting the GitHub API rate limit while generating the changelog. 
    1. Confirm base branch is correct.
    2. Input the new version number. You can omit the build metadata containing the purchases-hybrid-common version (e.g. `+13.2.0`). This will be added automatically for stable releases. 
    3. Optionally update `CHANGELOG.latest.md` when the auto-generated one is not sufficient. Call out public API changes (if any).
    4. A new branch and PR will automatically be created.
3. Review the PR and approve it if it's okay.
4. Then approve the hold job created in CircleCI. CircleCI will now create a tag for the version, and continue the release.
5. The release will be published automatically by CircleCI.
6. After that, you can merge the release PR to `main`, and merge the PR bumping to the next `-SNAPSHOT` version right after.

