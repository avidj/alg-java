package org.avidd.string;

public class BruteForceExplicitBackupTest extends StringSearchTest {

  @Override
  StringSearch stringSearch() {
    return new BruteForceExplicitBackup();
  }
}
