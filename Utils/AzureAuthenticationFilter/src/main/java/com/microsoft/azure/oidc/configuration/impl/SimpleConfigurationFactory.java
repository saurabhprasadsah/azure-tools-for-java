/*******************************************************************************
 * Copyright (c) Microsoft Corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.microsoft.azure.oidc.configuration.impl;

import java.util.List;
import java.util.Map;

import com.microsoft.azure.oidc.common.algorithm.Algorithm;
import com.microsoft.azure.oidc.common.issuer.Issuer;
import com.microsoft.azure.oidc.common.name.Name;
import com.microsoft.azure.oidc.configuration.Configuration;
import com.microsoft.azure.oidc.configuration.ConfigurationFactory;
import com.microsoft.azure.oidc.configuration.endpoint.EndPoint;
import com.microsoft.azure.oidc.configuration.key.Key;
import com.microsoft.azure.oidc.exception.PreconditionException;

public final class SimpleConfigurationFactory implements ConfigurationFactory {
	private static final ConfigurationFactory INSTANCE = new SimpleConfigurationFactory();

	@Override
	public Configuration createConfiguration(final List<Algorithm> algorithms, final Map<Name, Key> keys,
			final Issuer issuer, final EndPoint authenticationEndPoint, final EndPoint logoutEndPoint) {
		if (algorithms == null || keys == null || issuer == null || authenticationEndPoint == null
				|| logoutEndPoint == null) {
			throw new PreconditionException("Required parameter is null");
		}
		if (algorithms.isEmpty()) {
			throw new PreconditionException("Algorithm list is empty");
		}
		return new SimpleConfiguration(algorithms, keys, issuer, authenticationEndPoint, logoutEndPoint);
	}

	public static ConfigurationFactory getInstance() {
		return INSTANCE;
	}
}
