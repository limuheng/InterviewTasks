/*******************************************************************************
 *  Copyright (c) 2011 GitHub Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *    Kevin Sawicki (GitHub Inc.) - initial API and implementation
 *******************************************************************************/

package com.muheng.media17task.data

import okhttp3.Headers

const val HEADER_LINK = "Link"
const val HEADER_NEXT = "X-Next"
const val HEADER_LAST = "X-Last"
const val META_REL = "rel"
const val META_LAST = "last"
const val META_NEXT = "next"
const val META_FIRST = "first"
const val META_PREV = "prev"

const val DELIM_LINKS = ","
const val DELIM_LINK_PARAM  = ";"

/**
 * Page link class to be used to determine the links to other pages of request
 * responses encoded in the current response. These will be present if the
 * result set size exceeds the per page limit.
 */
class PageLinks(headers: Headers) {

    private var first: String? = null
    private var last: String? = null
    private var next: String? = null
    private var prev: String? = null

    init {
        val linkHeader = headers.get(HEADER_LINK)
        if (linkHeader != null) {
            val links = linkHeader.split(DELIM_LINKS)
            for (link in links) {
                val segments = link.split(DELIM_LINK_PARAM.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (segments.size < 2) {
                    continue
                }

                var linkPart = segments[0].trim { it <= ' ' }
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) {
                    //$NON-NLS-1$ //$NON-NLS-2$
                    continue
                }

                linkPart = linkPart.substring(1, linkPart.length - 1)

                for (i in 1 until segments.size) {
                    val rel = segments[i].trim { it <= ' ' }.split("=".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray() //$NON-NLS-1$
                    if (rel.size < 2 || !META_REL.equals(rel[0]))
                        continue

                    var relValue = rel[1]
                    if (relValue.startsWith("\"") && relValue.endsWith("\""))
                        //$NON-NLS-1$ //$NON-NLS-2$
                        relValue = relValue.substring(1, relValue.length - 1)

                    if (META_FIRST.equals(relValue))
                        first = linkPart
                    else if (META_LAST.equals(relValue))
                        last = linkPart
                    else if (META_NEXT.equals(relValue))
                        next = linkPart
                    else if (META_PREV.equals(relValue))
                        prev = linkPart
                }
            }
        } else {
            next = headers.get(HEADER_NEXT)
            last = headers.get(HEADER_LAST)
        }
    }

    fun getFirst(): String? {
        return first
    }

    fun getLast(): String? {
        return last
    }

    fun getNext(): String? {
        return next
    }

    fun getPrev(): String? {
        return prev
    }

}