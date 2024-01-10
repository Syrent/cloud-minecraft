//
// MIT License
//
// Copyright (c) 2024 Incendo
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package cloud.commandframework.brigadier.suggestion;

import cloud.commandframework.internal.CommandNode;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Brigadier suggestion provider that delegates to Cloud's suggestion provider.
 *
 * @param <C> the Cloud command sender type
 * @param <S> the Brigadier command sender type
 * @since 2.0.0
 */
@API(status = API.Status.INTERNAL, since = "2.0.0")
public final class CloudDelegatingSuggestionProvider<C, S> implements SuggestionProvider<S> {

    private final BrigadierSuggestionFactory<C, S> brigadierSuggestionFactory;
    private final CommandNode<C> node;

    /**
     * Creates a new suggestion provider.
     *
     * @param suggestionFactory the factory that produces suggestions
     * @param node              the node to generate suggestions for
     */
    public CloudDelegatingSuggestionProvider(
            final @NonNull BrigadierSuggestionFactory<C, S> suggestionFactory,
            final @NonNull CommandNode<C> node
    ) {
        this.brigadierSuggestionFactory = suggestionFactory;
        this.node = node;
    }

    @Override
    public @NonNull CompletableFuture<Suggestions> getSuggestions(
            final @NonNull CommandContext<S> context,
            final @NonNull SuggestionsBuilder builder
    ) throws CommandSyntaxException {
        return this.brigadierSuggestionFactory.buildSuggestions(
                context,
                this.node.parent(),
                this.node.component(),
                builder
        );
    }
}
