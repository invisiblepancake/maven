/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.it;

import java.io.File;

import org.junit.jupiter.api.Test;

public class MavenIT0008SimplePluginTest extends AbstractMavenIntegrationTestCase {

    public MavenIT0008SimplePluginTest() {
        super(ALL_MAVEN_VERSIONS);
    }

    /**
     * Simple goal decoration where a plugin binds to a phase and the plugin must
     * be downloaded from a remote repository before it can be executed. This
     * test also checks to make sure that mojo parameters are aligned to the
     * project basedir when their type is "java.io.File".
     *
     * @throws Exception in case of failure
     */
    @Test
    public void testit0008() throws Exception {
        File testDir = extractResources("/it0008");
        Verifier verifier = newVerifier(testDir.getAbsolutePath());
        verifier.setAutoclean(false);
        verifier.deleteDirectory("target");
        verifier.deleteArtifact("org.apache.maven.its.plugins", "maven-it-plugin-touch", "1.0", "maven-plugin");
        verifier.addCliArgument("process-sources");
        verifier.execute();
        verifier.verifyFilePresent("target/touch.txt");
        verifier.verifyFilePresent("target/test-basedir-alignment/touch.txt");
        verifier.verifyErrorFreeLog();
    }
}
