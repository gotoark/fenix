/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.settings.quicksettings

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifyAll
import org.junit.Test

class QuickSettingsInteractorTest {
    private val controller = mockk<QuickSettingsController>(relaxed = true)
    private val interactor = QuickSettingsInteractor(controller)

    @Test
    fun `onReportProblemSelected should delegate the controller`() {
        val websiteUrl = "https://host.com/page1"
        val url = slot<String>()

        interactor.onReportProblemSelected(websiteUrl)

        verify {
            controller.handleReportTrackingProblem(capture(url))
        }
        assertAll {
            assertThat(url.isCaptured).isTrue()
            assertThat(url.captured).isEqualTo(websiteUrl)
        }
    }

    @Test
    fun `onProtectionToggled should delegate the controller`() {
        val trackingEnabled = true
        val trackingStatus = slot<Boolean>()

        interactor.onProtectionToggled(trackingEnabled)

        verifyAll {
            controller.handleTrackingProtectionToggled(capture(trackingStatus))
        }
        assertAll {
            assertThat(trackingStatus.isCaptured).isTrue()
            assertThat(trackingStatus.captured).isEqualTo(trackingEnabled)
        }
    }

    @Test
    fun `onProtectionSettingsSelected should delegate the controller`() {
        interactor.onProtectionSettingsSelected()

        verify {
            controller.handleTrackingProtectionSettingsSelected()
        }
    }

    @Test
    fun `onTrackingProtectionShown should delegate the controller`() {
        interactor.onTrackingProtectionShown()

        verify {
            controller.handleTrackingProtectionShown()
        }
    }

    @Test
    fun `onPermissionsShown should delegate the controller`() {
        interactor.onPermissionsShown()

        verify {
            controller.handlePermissionsShown()
        }
    }

    @Test
    fun `onPermissionToggled should delegate the controller`() {
        val websitePermission = mockk<WebsitePermission>()
        val permission = slot<WebsitePermission>()

        interactor.onPermissionToggled(websitePermission)

        verify {
            controller.handlePermissionToggled(capture(permission))
        }
        assertAll {
            assertThat(permission.isCaptured).isTrue()
            assertThat(permission.captured).isEqualTo(websitePermission)
        }
    }
}
