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
    2. Input the new version number.
    3. Optionally update `CHANGELOG.latest.md` when the auto-generated one is not sufficient. Call out public API changes (if any).
    4. A new branch and PR will automatically be created.
3. Review the PR and approve it if it's okay.
4. The `wait-for-pr-approval` job in CircleCI is then approved automatically, and CircleCI tags the version and continues the release once the tests pass.
5. The release will be published automatically by CircleCI.
6. The release PR is merged automatically once the release is published. Merge the PR bumping to the next `-SNAPSHOT` version right after.

## Hotfix releases
To release a fix merged into `main` without releasing the rest of `main`, for example 3.9.1 when the latest is 4.0.0, or 4.0.1 while `main` is not ready to release:

1. Comment `@RCGitBot hotfix 3` on the merged fix PR. The bot cherry-picks the fix onto the latest 3.x tag, bumps the version, opens a `release/3.9.1` PR into a new `hotfix/3.9.0` branch and comments the release PR link on the fix PR.
1. Approve the release PR, but **don't merge it**. Once tests pass, CircleCI tags 3.9.1, deploys it and merges the PR into `hotfix/3.9.0`. If that does not happen, approve the `wait-for-pr-approval` job in CircleCI by hand, or tag the last commit of `release/3.9.1` with `3.9.1` and push the tag.
1. Delete the `hotfix/3.9.0` branch and add the release to CHANGELOG.md and VERSIONS.md on `main`.

If the cherry-pick conflicts, the bot comments a local command instead: run it from `main`, resolve the conflicts, run `git cherry-pick --continue`, then run it again to open the PR.

To patch an older minor (3.8.x while 3.9.0 exists), run `bundle exec fastlane hotfix pr:<number> version:3.8.2` locally (releases 3.8.3). Both local commands need `fastlane/.env`, like `bump`.
