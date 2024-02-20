# Releasing

1. Update the `version` in `build.gradle.kts` to the release version.

2. Commit

   ```
   $ git commit -am "Prepares version A.B.C-D.E.F-G.H.I."
   ```

3. Tag

   ```
   $ git tag -am "Version A.B.C-D.E.F-G.H.I" A.B.C-D.E.F-G.H.I
   ```

4. Update the `version` in `build.gradle.kts` to the next "SNAPSHOT" version.

5. Commit

   ```
   $ git commit -am "Prepares the next development version."
   ```

6. Push

   ```
   $ git push && git push --tags
   ```

   This will trigger a GitHub Action workflow which will upload the release artifacts to Maven
   Central, and create a GitHub release.
