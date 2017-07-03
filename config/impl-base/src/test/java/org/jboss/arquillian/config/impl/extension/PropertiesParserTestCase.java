/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.config.impl.extension;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * SystemPropertiesReaderTestCase
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class PropertiesParserTestCase {
    private static final String ENGINE_PROP_DEPLOYMENTS = "arq.engine.deploymentExportPath";
    private static final String ENGINE_PROP_MAXCLASS = "arq.engine.maxTestClassesBeforeRestart";
    private static final String ENGINE_VAL_DEPLOYMENTS = "target";
    private static final String ENGINE_VAL_MAXCLASSES = "2";

    private static final String CONFIGURATION_PROP_1 = "jbossHome";

    private static final String CONTAINER_PROP_CONFIGURATION_1 =
        "arq.container.jboss.configuration." + CONFIGURATION_PROP_1;
    private static final String CONTAINER_VAL_CONFIGURATION_1 = "target/jboss-as";
    private static final String CONTAINER_PROP_PROTOCOL_1 =
        "arq.container.jboss.protocol.Servlet 3.0." + CONFIGURATION_PROP_1;
    private static final String CONTAINER_VAL_PROTOCOL_1 = "192.0.0.1";

    private static final String CONTAINER_PROP_1 = "arq.container.jboss.mode";
    private static final String CONTAINER_VAL_1 = "suite";

    private static final String GROUP_PROP_CONTAINER_CONFIGURATION_1 =
        "arq.group.cluster.container.jboss.configuration." + CONFIGURATION_PROP_1;
    private static final String GROUP_VAL_CONTAINER_CONFIGURATION_1 = CONTAINER_VAL_1;
    private static final String GROUP_PROP_CONTAINER_PROTOCOL_1 =
        "arq.group.Cluster 1.container.JBoss AS 7.protocol.Servlet 3.0." + CONFIGURATION_PROP_1;
    private static final String GROUP_VAL_CONTAINER_PROTOCOL_1 = CONTAINER_VAL_1;

    private static final String GROUP_PROP_CONTAINER_1 = "arq.group.cluster.container.jboss.mode";
    private static final String GROUP_VAL_CONTAINER_1 = "suite";

    private static final String GROUP_PROP_1 = "arq.group.cluster.default";
    private static final String GROUP_VAL_1 = "true";

    private static final String DEFAULT_PROTOCOL_PROP_1 = "arq.defaultprotocol.Servlet 3.0." + CONFIGURATION_PROP_1;
    private static final String DEFAULT_PROTOCOL_VAL_1 = "true";

    private static final String EXTENSION_PROP_1 = "arq.extension.extension-1." + CONFIGURATION_PROP_1;
    private static final String EXTENSION_VAL_1 = "suite";

    private ArquillianDescriptor desc;

    @Before
    public void createDescriptor() {
        desc = create();
    }

    @After
    public void print() {
        desc.exportTo(System.out);
    }

    @Test
    public void shouldBeAbleToSetEngineProperties() {
        validate(ENGINE_PROP_DEPLOYMENTS, ENGINE_VAL_DEPLOYMENTS, new ValueCallback() {
            @Override
            public String get() {
                return desc.engine().getDeploymentExportPath();
            }
        });
        validate(ENGINE_PROP_MAXCLASS, ENGINE_VAL_MAXCLASSES, new ValueCallback() {
            @Override
            public String get() {
                return String.valueOf(desc.engine().getMaxTestClassesBeforeRestart());
            }
        });
    }

    @Test
    public void shouldBeAbleToAddContainer() {
        validate(CONTAINER_PROP_1, CONTAINER_VAL_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getContainers().get(0).getMode();
            }
        });
    }

    @Test
    public void shouldBeAbleToAddContainerConfiguration() {
        validate(CONTAINER_PROP_CONFIGURATION_1, CONTAINER_VAL_CONFIGURATION_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getContainers().get(0).getContainerProperties().get(CONFIGURATION_PROP_1);
            }
        });
    }

    @Test
    public void shouldBeAbleToAddContainerProtocol() {
        validate(CONTAINER_PROP_PROTOCOL_1, CONTAINER_VAL_PROTOCOL_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getContainers()
                    .get(0)
                    .getProtocols()
                    .get(0)
                    .getProtocolProperties()
                    .get(CONFIGURATION_PROP_1);
            }
        });
    }

    @Test
    public void shouldBeAbleToAddGroup() {
        validate(GROUP_PROP_1, GROUP_VAL_1, new ValueCallback() {
            @Override
            public String get() {
                return String.valueOf(desc.getGroups().get(0).isGroupDefault());
            }
        });
    }

    @Test
    public void shouldBeAbleToAddGroupContianer() {
        validate(GROUP_PROP_CONTAINER_1, GROUP_VAL_CONTAINER_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getGroups().get(0).getGroupContainers().get(0).getMode();
            }
        });
    }

    @Test
    public void shouldBeAbleToAddGroupContianerConfiguration() {
        validate(GROUP_PROP_CONTAINER_CONFIGURATION_1, GROUP_VAL_CONTAINER_CONFIGURATION_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getGroups()
                    .get(0)
                    .getGroupContainers()
                    .get(0)
                    .getContainerProperties()
                    .get(CONFIGURATION_PROP_1);
            }
        });
    }

    @Test
    public void shouldBeAbleToAddGroupContianerProtocol() {
        validate(GROUP_PROP_CONTAINER_PROTOCOL_1, GROUP_VAL_CONTAINER_PROTOCOL_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getGroups()
                    .get(0)
                    .getGroupContainers()
                    .get(0)
                    .getProtocols()
                    .get(0)
                    .getProtocolProperties()
                    .get(CONFIGURATION_PROP_1);
            }
        });
    }

    @Test
    public void shouldBeAbleToAddDefaultProtocol() {
        validate(DEFAULT_PROTOCOL_PROP_1, DEFAULT_PROTOCOL_VAL_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getDefaultProtocol().getProperties().get(CONFIGURATION_PROP_1);
            }
        });
    }

    @Test
    public void shouldBeAbleToAddExtension() {
        validate(EXTENSION_PROP_1, EXTENSION_VAL_1, new ValueCallback() {
            @Override
            public String get() {
                return desc.getExtensions().get(0).getExtensionProperties().get(CONFIGURATION_PROP_1);
            }
        });
    }

    private void validate(String property, String value, ValueCallback callback) {
        try {
            System.setProperty(property, value);

            new PropertiesParser().addProperties(desc, System.getProperties());

            Assert.assertEquals(value, callback.get());
        } finally {
            System.clearProperty(property);
        }
    }

    private ArquillianDescriptor create() {
        return Descriptors.create(ArquillianDescriptor.class);
    }

    public interface ValueCallback {
        String get();
    }
}
